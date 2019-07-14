# android-clean-architecture

[![Kotlin](https://kotlin.link/awesome-kotlin.svg)](https://kotlinlang.org/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.21-blue.svg)](http://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A movies sample android project in Kotlin which uses clean architecture

### Clean Architecture Description and Implementation

This project uses androidx and android lifecycle components ViewModel, LiveData and Navigation architecture components to display a list of Popular movies from [The Movie Database](https://www.themoviedb.org) which has been added as a file in the Assets folder. Selecting a movie will display a movie detail page including title, overview, image, the popularity of the movie in terms of the votes received and the genres it belongs to.

The architecture is based on Robert C. Martin's (Uncle Bob) original [Clean Architecture blog post](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) and accompanying schematic diagram. It stresses a design which is based on inversion of control and enforcing inner layers knowing nothing about how they are being used in the outer layers. This is composed of 3 different layers:

* Data (Web API responses, Repository and Datasources plus other interfaces)
* Domain (Entities, Use Cases, Interfaces for flow of control implemented by Data layer)
* Presentation (Activities, Fragments, ViewModel)

Domain contains the Entities which in this case are data classes with app wide logic and data. Use cases are the business operations on the Entities which are invoked by the presentation layer for display and fulfilled by the data layer. The domain has interfaces which are implemented by outer layers such as MovieRepository and data structures of UseCase Input and UseCase Output to define the boundaries between the domain and other layers.
The presentation and data layers are only aware of the domain layer and not each other. Mappers are used by the data layer to convert API responses, database results etc. from the data layer to the domain layer.

### Package Details and Flow

The app is packaged by feature underneath which is the core package containing common classes for all layers and in this case the movies package which has data, domain and presentation packages. This structure enables new developers to quickly get up-to-speed as it is familiar and repeatable.
\
The controller in the Clean architecture diagram is typically user interaction from a fragment observing a live data object in a ViewModel. ViewModel have access to use cases which reference repositories. Strict port conventions are followed so data is passed through a UseCaseInput port into the Use Case. The results of the Use Case logic often referred to as an interactor are then returned to the presenter through a UseCaseOutput port. This can contain entities to display in the presentation layer as well as Failures. All results update LiveData in the ViewModel which updates the UI through observers in fragments.
The controller and presenter in this Clean Architecture pattern are both implemented by the View Model.

### Frameworks and Libraries

* Architecture components with Navigation, ViewModel and LiveData
* Moshi for Json Parsing
* Koin for dependency injection
* AndroidX
* Picasso for Image Loading

\
Unit tests use [JUnit 5](https://junit.org/junit5) and [Mockk](https://github.com/mockk/mockk)\
Integration Tests use [Espresso](https://developer.android.com/training/testing/espresso)

### License

    Copyright 2019 Alex Forrester

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

