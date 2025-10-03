# ImageStat

## Overview

**ImageStat** is a Java application that compares two images of the same size and generates a third output image highlighting the differences. It also calculates the Mean Squared Error (MSE) between the two images, providing a quantitative measure of their similarity.

## Features

- Compares two images pixel by pixel.
- Calculates the MSE (Mean Squared Error) across all color channels.
- Generates an output image where the intensity of red indicates the difference between corresponding pixels.
- Supports multi-threaded processing for faster computation on multi-core systems.
- Command-line interface.

## Usage

```sh
java ImageStat.java <img1> <img2> <output> [-t threads]
```

- `<img1>`: Path to the first image file.
- `<img2>`: Path to the second image file.
- `<output>`: Path where the output image will be saved.
- `-t threads` (optional): Number of threads to use for processing. If not specified, the program uses the number of available processors.

**Example:**
```sh
java ImageStat.java image1.png image2.png diff.png -t 4
```

## Output

- **MSE Value:** Printed to the console after processing.
- **Output Image:** Saved as a PNG file. The red intensity of each pixel represents the difference between the corresponding pixels in the input images.

## Requirements

- Java 8 or higher.
- Input images must have the same dimensions.

## Notes

- The program will exit with an error message if the input images have different sizes or if the arguments are incorrect.
- The number of threads cannot exceed the image height.
