# Compiler final project (Cool)
## Shahid beheshti university 

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/AAdvari/compiler-phase3)


this compiler is developed for compiler design course - fall 2020

- based on 32 bit mips architecture
- no dynamic runtime allocating

> The project only meets the requirements which were
> highlighted by course assistants and is not a full compiler 
> for real usage. 

# Table of Contents
1. [Tech](#Tech)
2. [Installation](#Installation)
3. [Contributing](#Contributing)
4. [Roadmap](#Roadmap)
5. [License](#License)





## Tech <a name="Tech"></a>

Cool Compiler uses a number of projects to work properly:

- [PGen] - Parser generator (based on Syntax graphs concepts)
- [JFlex] - Lexical analyser generator 
- [Dillinger] - Markdown editor 

## Installation <a name="Installation"></a>

In order to run project having java installed:

1. Put .cool app, compiler-phase3.jar and table.npt ( available at parser package) in a directory

2. Run the program by following command:
    ```sh
      java -jar compiler-phase3.jar --input [YOUR_IBPUT_FILE_DIR] --output [OUTPUT_FILE_DIR] --table [TABLE DIR]
    ```
## Contributing <a name="Contributing"></a>

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Roadmap <a name="Roadmap"></a>

- [x] Add runtime exception handling feature
- [ ] Add object oriendted programming support
- [ ] Add higher-order functions 

## License <a name="License"></a>

MIT

**Free Software, Hell Yeah!**


   [PGen]: <https://github.com/Borjianamin98/PGen>
   [JFlex]: <https://jflex.de/>
   [Dillinger]: <https://dillinger.io/>
   

