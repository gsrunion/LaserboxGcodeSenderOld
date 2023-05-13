# LaserboxGcodeSender
An alternative to XCS for sending 3rd party generated gcode to the XTools series of Co2 laser cutters

## Background
Until recently, with the beta of XTool Creative Suite (XCS), users of XTool Co2 lasers were required to relay on XTool's proprietary software, LaserBox, as a gcode generator and sender. In short, the software is a dumpster fire and a source of much angst for owners of these machines. As an answer to thie problem XTool is working on adding support for their Co2 lasers to their new fangled XCS software. Since this software works with their other series of lasers, it is a road well travelled and is more likely to get bug fixes and feature additions. However, anyone that has used XTools created software knows that software development is not their strong suite and I am of the opinion that many of the same type of bugs to be found in the LaserBox will manifest in XCS. However, XTool has added support to XCS to transform and upload gcode generated from third party tools such as LightBurn. While a big step in the right direction the implementation has the following limitations

1. Support for exposing the laser's built in camera, such that it can be used for work placement, is not present and user have to rely on absolute positioning.
2. Support for using the rotary is not present in this workflow.
3. Support for measuring material thickness, to auto adjust focus is not present though there are ways to work around this. 
4. XCS is still a man in the middle and subject to XTools continued lack of softwar quality.

## GCode translate and send support in XCS
The gcode support in XCS is actually a pretty thin shim of functionality. It ingests a provided gcode file, does some translation, and uploads a zipped version to the machine via the same HTTP call used when gcode is generated from the XCS or LaserBox softwares. Ingest file -> transform -> upload is not a particularly complex software operation and something that can be relatively easily recreated without xtools software. Some of the gcode transformations done by XCS are:

1. Negating X and Y coordinates
    - Reasoning: For some reason XTool decide to use a negative coordinate system for the LaserBox machine. Home, (0,0), is in the left rear and coordinates to the right and forward are negative and coordinates to the back and left are positive.
    
2. Removal of the G90 (use absolute coordinates) command
    - Reasoning: The machine seemingly only works in aboslute coordinates and does not appear to have support for relative coordinates.
    
3. Formatting X,Y,Z coordinates as decimal values with ever present 3 decimal places
    - Reasoning: Unknown
    
4. Insertion of spaces around in between between gcode parameters
    - Reasoning: Unknown, but gcode it easier to parse when spaces are present

5. More to be discovered
    
 
### Gcode Transformation Example: Transformed on the left, input from LightBurn on the right
![transformed gcode](https://user-images.githubusercontent.com/1959884/214913335-6fd0ba51-f6ad-4014-8d45-78c4166a67ac.png)

## Project Goals
1. To provide an alternate solution to XCS's transform and send functionality
2. Provide a workflow with less steps than XCS to upload gcode to the machine. The expected user workflow is 
    - Save source gcode file to designated location on file system
    - Press button on LaserBox to start the job
3. To provide a platform for which the limitations of the XCS solution can be addressed. Future goals include
    - Integration with relatively cheap 3rd party cameras that are compatible with LightBurn allowing work placement via camera
    - Rotary support
    - Finish what my defunct [LightBurnGrblBridge](https://github.com/gsrunion/LaserBoxGrblBridge) project to create a fully function facade layer that make the LaserBox appear as a run of the mill GRBL machine.
4. To bake in support for data collection to allow for easier debug of user issues. That is too
    - Provide copious logging
    - Capture timestamped copies both provided and transformed gcode files and archive.
    - Utilize GitHub as a bug tracking platform vesus some absurd User Experience Map Google document.


### Project Structure
The project will consist of a series of folders and couple of simple daemons that monitor these folders transforming and moving the files between them.

Folders:
1. Input folder: the folder on the system for which users will save gcode exported from lightburn
2. Processing folder: the folder that will temporarily hold renamed input files and transformed files
3. Ouput folder: a folder that will house transformed gcode up until the point in which it is uploaded to the LaserBox
4. Archive folder: Contains copies of all files saved to the input folder and uploaded to the LaserBox with the copies names such that they are easily matched up for debug.

Deamons:
1. Input Deamon
    - Duties
        - Monitor the input folder for file additions
        - Uniquely rename and save a copy of the file to the processing folder
2. Processing Deamon:
    - Duties
        - Monitor processing folder for file additions
        - Create transformed copy of the input file
        - save a copy of the input and transformed file to the archive
3. Output Deamon
    - Duties
        - Monitor the output folder for file additions
        - Upload transformed file to the Laserbox
        - Delete file after upload
