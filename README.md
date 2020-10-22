# Domi'Nation

[![Build Status](https://travis-ci.com/baudev/Domi-Nation.svg?token=X4oFpxDHQaxGHoYnYpJz&branch=dev)](https://travis-ci.com/baudev/Domi-Nation)
[![codecov](https://codecov.io/gh/baudev/Domi-Nation/branch/dev/graph/badge.svg?token=IdG3unEbi5)](https://codecov.io/gh/baudev/Domi-Nation)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Board game developed in Java and using the JavaFX library. This game is inspired from [KingDomino one](http://www.blueorangegames.eu/pf/kingdomino_fr/). 

![](https://user-images.githubusercontent.com/29781702/96864101-35ff2500-1468-11eb-929b-a4e6e8167c5c.png)

## INSTALLATION
```
git clone https://github.com/baudev/Domi-Nation.git
gradle run
```

### RUN TESTS

- Without coverage:
```
gradle check
```

- With coverage (the `view` directory was exclude):
```
gradle check
gradle junitPlatformJacocoReport
```

### NOTE
All documentation can be found in the `/docs` directory or at https://baudev.github.io/Domi-Nation/.


### CREDITS

The resource pictures are coming from the [MIT Project](https://github.com/Adam-Carmichael/KingDominoProject) of [Adam-Carmichael](https://github.com/Adam-Carmichael).

### LICENSE  
  
```  
MIT License

Copyright (c) 2018 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

