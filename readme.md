# Ray Tracer

This is a ray tracer implemented in Java. It allows to use a simple pre-defined language to define a scene as a file, and use it as an input to the ray tracer. The ray tracer will recreate the scene and save output image.

![alt text](https://github.com/alexeyza/raytracer/raw/master/examples/example1.png "Example 1")

![alt text](https://github.com/alexeyza/raytracer/raw/master/examples/example3_variation.png "Example 2")

## How to Use
Follow these steps:

1. Create a `.txt` file to describe the desired scene (world). For examples of some scene files, check the `/examples` directory. In general, all directions are described as a 3D vector, while all colors and lights are described as an RGB value.
2. Call the main application with the `txt` file as the input.

## Beta
This software is not perfect and may not work properly (currently it is missing some advanced features).

---
Copyright (C) 2013 Alexey Zagalsky

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.