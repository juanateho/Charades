package com.example.charades.model

object Words {
    val animals = mapOf(
        "en" to listOf(
            "Cat", "Dog", "Elephant", "Lion", "Tiger", "Bear", "Wolf", "Horse", "Monkey", "Rabbit", "Fox", "Cow", "Sheep", "Pig", "Mouse"
        ),
        "es" to listOf(
            "Gato", "Perro", "Elefante", "León", "Tigre", "Oso", "Lobo", "Caballo", "Mono", "Conejo", "Zorro", "Vaca", "Oveja", "Cerdo", "Ratón"
        )
    )
    val movies = mapOf(
        "en" to listOf(
            "Titanic", "Avatar", "Inception", "Jaws", "Rocky", "Frozen", "Gladiator", "Up", "Shrek", "Toy Story", "Matrix", "Star Wars", "Jurassic Park", "Spider-Man", "Batman"
        ),
        "es" to listOf(
            "Titanic", "Avatar", "Origen", "Tiburón", "Rocky", "Frozen", "Gladiador", "Up", "Shrek", "Toy Story", "Matrix", "Star Wars", "Jurassic Park", "Spider-Man", "Batman"
        )
    )
    val professions = mapOf(
        "en" to listOf(
            "Doctor", "Teacher", "Engineer", "Chef", "Pilot", "Firefighter", "Police", "Nurse", "Dentist", "Artist", "Musician", "Farmer", "Lawyer", "Actor", "Writer"
        ),
        "es" to listOf(
            "Médico", "Profesor", "Ingeniero", "Chef", "Piloto", "Bombero", "Policía", "Enfermero", "Dentista", "Artista", "Músico", "Agricultor", "Abogado", "Actor", "Escritor"
        )
    )
}
