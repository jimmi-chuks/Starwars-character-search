package com.dani.shared_test.fakes

import com.dani.model.dto.*

object TestData {

    val vehicle = Vehicle(
        cargoCapacity = "50000",
        consumables = "2 months",
        costInCredits = "150000",
        created = "2014-12-10T15:36:25.724000Z",
        crew = "46",
        edited = "2014-12-10T15:36:25.724000Z",
        length = "36.8",
        manufacturer = "Corellia Mining Corporation",
        maxAtmospheringSpeed = "30",
        model = "Digger Crawler",
        name = "Sand Crawler",
        passengers = "30",
        pilots = emptyList(),
        films = listOf("https://swapi.dev/api/films/1/"),
        url = "https://swapi.dev/api/vehicles/4/",
        vehicleClass = "wheeled"
    )

    val starship1 = Starship(
        mglt = "10 MGLT",
        cargoCapacity = "1000000000000",
        consumables = "3 years",
        costInCredits = "1000000000000",
        created = "2014-12-10T16:36:50.509000Z",
        crew = "342953",
        edited = "2014-12-10T16:36:50.509000Z",
        hyperdriveRating = "4.0",
        length = "120000",
        manufacturer = "Imperial Department of Military Research, Sienar Fleet System",
        maxAtmospheringSpeed = "n/a",
        model = "DS-1 Orbital Battle Station",
        name = "Death Star",
        passengers = "843342",
        films = listOf("https://swapi.dev/api/films/1/"),
        pilots = emptyList<String>(),
        starshipClass = "Deep Space Mobile Battlestation",
        url = "https://swapi.dev/api/starships/9/"
    )

    val character1 = Character(
        name = "Peel",
        birthYear = "98BY",
        skinColor = "NA",
        mass = "44",
        height = "167",
        species = listOf("https://swapi.dev/api/species/1/", "https://swapi.dev/api/species/2/"),
        eyeColor = "blue",
        films = listOf("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/2/"),
        gender = "NA",
        hairColor = "black",
        homeWorld = "https://swapi.dev/api/planet/3/",
        created = "2014-12-10T16:44:31.486000Z",
        edited = "Joe",
        starships = listOf("https://swapi.dev/api/starships/12/", "https://swapi.dev/api/starships/22/"),
        url = "https://swapi.dev/api/people/1/",
        vehicles = listOf("https://swapi.dev/api/vehicles/2/")
    )
    val character2 = character1.copy(
        name = "Jon",
        url = "https://swapi.dev/api/people/2/",
        species = emptyList(),
        starships = listOf("https://swapi.dev/api/starships/12/")
    )

    val character3 = character2.copy(
        name = "Conan",
        url = "https://swapi.dev/api/people/3/",
        height = "112"
    )

    val searchResult1 = SearchResult(
        count = 1,
        next = "https://swapi.dev/api/people/2/",
        previous = "https://swapi.dev/api/species/1/",
        results = listOf(character1)
    )

    val searchResult2 = SearchResult(
        count = 2,
        next = "https://swapi.dev/api/people/3/",
        previous = "https://swapi.dev/api/species/2/",
        results = listOf(character2, character3)
    )

    val movie1 = Movie(
        characters = listOf(character1.url, character2.url),
        title = "Movie title",
        producer = "NA",
        releaseDate = "23-45-66",
        director = "The Director",
        species = listOf("https://swapi.dev/api/species/1/", "https://swapi.dev/api/species/2/"),
        openingCrawl = "Random_crawl",
        episodeId = 1,
        planets = listOf("https://swapi.dev/api/planets/1/", "https://swapi.dev/api/planets/22/"),
        created = "2014-12-10T16:44:31.486000Z",
        edited = "Joe",
        starships = listOf("https://swapi.dev/api/starships/12/", "https://swapi.dev/api/starships/22/"),
        url = "https://swapi.dev/api/films/1/",
        vehicles = listOf("https://swapi.dev/api/vehicles/2/")
    )

    val movie2 = movie1.copy(url = "https://swapi.dev/api/films/2/")

    val specie1 = Specie(
        averageHeight = "2.1",
        averageLifespan = "400",
        classification = "Mammal",
        created = "2014-12-10T16:44:31.486000Z",
        designation = "Sentient",
        edited = "2014-12-10T16:44:31.486000Z",
        eyeColors = "blue, green, yellow, brown, golden, red",
        hairColors = "black, brown",
        homeWorld = "https://swapi.dev/api/planets/14/",
        language = "Shyriiwook",
        name = "Wookie",
        people = listOf("https://swapi.dev/api/people/1/"),
        films = listOf("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/2/"),
        skinColors = "gray",
        url = "https://swapi.dev/api/species/1/"
    )

    val specie2 = specie1.copy( url = "https://swapi.dev/api/species/1/")

    val planet1 = Planet(
        climate = "Arid",
        created = "2014-12-09T13:50:49.641000Z",
        diameter = "10465",
        edited = "2014-12-15T13:48:16.167217Z",
        films = listOf("https://swapi.dev/api/films/1/"),
        gravity = "1",
        name = "Tatooine",
        orbitalPeriod = "304",
        population = "120000",
        residents = listOf("https://swapi.dev/api/people/1/"),
        rotationPeriod = "23",
        surfaceWater = "1",
        terrain = "Dessert",
        url = "https://swapi.dev/api/planets/1/"
    )

    val planet2 = planet1.copy(url = "https://swapi.dev/api/planets/1/")

}