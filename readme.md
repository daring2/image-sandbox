Image-Sandbox allows to try and test functions and algorithms implemented in [OpenCV](http://opencv.org) library. 
It is implemented in Kotlin language and uses [JavaCV](https://github.com/bytedeco/javacv) to work with OpenCV.

Main features:
* extensible command set
* transformation pipeline support
* sandbox UI with configurable command parameters and auto-completion

How to use as a standalone application:
* download and unzip [image-sandbox-0.6.1.zip](https://bintray.com/daring/image-sandbox/download_file?file_path=image-sandbox-0.6.1.zip)
* run image-sandbox script 

Script examples:
```text
// load image i1.jpg, convert it to gray, crop 20% center area and display a result
read(i1.jpg); convert(gray); cropCenter(20);  show(i1)
```