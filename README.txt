
# HW9 - IME: Image Manipulation and Enhancement

This is a Java-based Image Processing program that follows the Model-View-Controller (MVC)
architectural pattern. The program allows users to load an image, apply various transformations, and
save the processed image as a file. The application is run from the command line using various
commands. It contains a model which acts as an image database, a controller which takes commands
to give to the model and writes output to the viewGUI, and a viewGUI to show output to the user.
Methods include an add which takes a String image id and an IImageState object and loads the image
into the hash map. The get method gets an image from the hash map given a String id.

Sample image generated on https://images.ai/

## Requirements
Java Development Kit (JDK) 8 or higher

## Run Configuration
A run configuration has been prepared which loads a sample image, runs all transformations on it and
saves all result images to the res folder. To run the run configuration, open the project and then
open the pandaEdit.run.xml file in IntelliJ. Once the file is opened, just press run.

## Interfaces
1. IPixelState - Defines the RGB components of an individual pixel along with getters to access
                 those components
2. IPixel - Extends the IPixelState interface to include setting individual color channel values to
            a given pixel
3. IImageState - Defines an image via an IPixel array, with getters for various parameters
4. IImage - Extends the IImageState interface to include a setter for setting individual pixels at a
            given row-col position in the IPixel array
5. IModel - Defines the model behavior including adding and getting images from an image database
6. ITransformation - Defines the strategies necessary to apply transformations to a given image
7. ICommand - Defines the commands necessary to call transformation strategies from the controller
8. IImageLoader - Defines the functionality needed to load images to the program
9. IImageSaver - Defines the functionality needed to save images to a given path
10. IController - Defines the controller behavior to give commands to the model and return output to
                  the viewGUI
11. IView - Defines the viewGUI behavior for outputting info to the user and logging activity

## Pixel Class
PixelImpl - represents a single pixel and holds int values for red, green, and blue. Methods include
            setters and getters for each color channel. The class utilizes a private clamp method
            that "clamps" pixel values to 0 if they are less than 0 or 255 if they are above 255 to
            make sure they are valid. This could be changed in the future.

## Image Class
ImageImpl - Fields include an IPixelState 2D array, and int fields for height/width of the image.
            The constructor initializes the height and width field based on input, and the
            IPixelState array is then initialized using those values. Methods include getters for
            the width, height, color channels, and the IPixelState array itself. There is also a
            setter for setting individual IPixel objects inside the IPixelState array which takes a
            row, col as well as r, g, b values. This class also utilizes the clamp method for the
            pixel setter.

## Model Class
ImageDatabase - This class implements the IModel interface. The only field is a
                Map<String, IImageState> object which stores any images loaded. The constructor
                initializes the Map object to a Hash Map.

## Transformation Classes
These classes implement the ITransformation interface with only an apply method which takes an
IImageState object and returns an IImageState object after applying a given transformation

1. BrightenTransform - Changes the brightness of an image by a given increment and has the increment
                       as an int field
2. RedTransform - This class defines the apply method to get the red channel component greyscale
3. GreenTransform - This class defines the apply method to get the blue channel component greyscale
4. BlueTransform - This class defines the apply method to get the green channel component greyscale
5. ValueTransform - This class defines the apply method to get the value channel component greyscale
6. IntensityTransform - This class defines the apply method to get the intensity channel component
                        greyscale
7. LumaTransform - This class defines the apply method to get the luma channel component greyscale
8. BlurTransform - This class defines the apply method to apply a gaussian blur filter to an image
9. SharpenTransform - This class defines the apply method to apply a sharpening filter to an image

## Command Classes
These classes implement the ICommand interface with only a void run method which takes an scanner
for the given input, the model, and the viewGUI. It calls the correct strategy to run on an image given
some input from the user.

1. BrightenCommand - This calls defines the run method to call the BrightnessTransform strategy
2. RedCommand - This class defines the run method to call the RedTransform strategy
3. GreenCommand - This class defines the run method to call the GreenTransform strategy
4. BlueCommand - This class defines the run method to call the BlueTransform strategy
5. ValueCommand - This class defines the run method to call the ValueTransform strategy
6. IntensityCommand - This class defines the run method to call the IntensityTransform strategy
7. LumaCommand - This class defines the run method to call the LumaTransform strategy
8. BlurCommand - This class defines the run method to call the BlurTransform strategy
9. SharpenCommand - This class defines the run method to call the SharpenTransform strategy
10. SepiaCommand - This class defines the run method to call the SepiaTransform strategy
11. LoadCommand - This class defines the run method to load an image into the database
12. SaveCommand - this class defines the run method to save an image to a given path

## Loader/Saver Classes
PPMImageLoader - This class defines the run method from the IImageLoader interface to load a PPM
                 formatted image to the image database
PPMImageSaver - This class defines the run method from the IImageSaver interface to save a PPM
                formatted image to a specified path
ImageLoader - This class defines the run method from the IImageLoader interface to load a JPEG, PNG,
              or BMP formatted image to a specified path
ImageSaver - This class defines the run method from the IImageLoader interface to load a JPEG, PNG,
              or BMP formatted image to a specified path

## Controller Class
ControllerImpl - Takes the model, the viewGUI, and a Readable input in the constructor and initializes
them. It also initializes a hash map to hold all the allowed commands which the user can input. The
controller then starts parsing the input for a command, and then hands the rest of the input to the
correct command class

## View  Class
ViewImpl - Takes the model and an optional Appendable log in its constructors and allows for a log
to record messages from the controller, as well as a toString method for printing out the individual
pixels of an image to the command line

## Commands
1. load - load an image to be processed
2. save - save an image to a specified path
3. brighten - brighten an image by a given increment
4. darken - darken an image by a given increment
5. red-component - get a red channel component greyscale version of the image
6. green-component - get a green channel component greyscale version of the image
7. blue-component - get a blue channel component greyscale version of the image
8. value-component - get a greyscale version of the image based on the max of the color channels
9. intensity-component - get a greyscale version of the image based on the average of the color channels
10. luma-component - get a greyscale version of the image based on the luma value of the color channels
- Luma value = 0.2126r + 0.7152g + 0.0722b
11. blur - get a blurred version of the image that has a gaussian blur filter applied
12. sharpen - get a sharpened version of the image that has a sharpen filter applied
13. sepia - get a sepia tone version of the image that has a sepia filter applied

## Design Changes from HW9
1. Added an int alpha value to the Pixel class, along with getters and setters. I needed to update
   most classes to add support for an alpha value.
2. To support JPEG, PND, and BMP formats I created a single ImageLoader and ImageSaver since all can
   be read and written using the same exact methods. I had to update the LoadCommand to either pick
   the PPMImageLoader if filetype is "ppm" or run the default ImageLoader. Savers are chosen in
   an if statement. For PPM's I did retain the loader and saver since it cannot be read or written
   using conventional ImageIO methods.
3. Refactored all the transforms except for BrightnessTransform, IntensityTransform, and
   ValueTransform as follows:

        FilterTransform: This implements everything necessary for Red, Green, Blue, Sepia, and Luma
                         Transforms which have all ben subsequently deleted
        KernelTransform: This implements everything needed for Blur and Sharpen Transforms

        All the individual matrices and kernels are coded in the commands and given to either
        FilterTransform or KernelTransform to use in their respective constructors. Further
        consolidation is likely possible.
4. The main method was updated to add support for running txt script files via a configuration.
   If "-file" is not detected, then the main method will proceed to take commands directly from the
   console.

## Design Changes from HW10
1. Added an IViewGUI interface, ViewGUI implementing class, ViewListener interface for all the
   functionality related to the GUI. ViewGUI creates the JFrame and all its contents, along with the
   emitters that trigger the new controller to run the handlers.
2. Added a ControllerGUI class that implements IController and ViewListener so that it has a run()
   method along with all the handlers that receive input from the emitters in ViewGUI and run the
   various operations. The original controller couldn't be used because it took a Readable object
   for input.
3. Since the ICommand implementing classes all take Scanners, they were no used for the handlers in
   ControllerGUI. ControllerGUI implements all the necessary logic within each handler that the
   ICommand implementing classes do.
4. Added code for if args.length == 0, then create and run the GUI. Now the program can run for the
   GUI, through a script file, or through a run configuration.
5. Added a HistogramPanel class as well as the calculateHistogram() method in VIewGUI to generate
   and display a histogram for any newly loaded image, as well as any transformed image