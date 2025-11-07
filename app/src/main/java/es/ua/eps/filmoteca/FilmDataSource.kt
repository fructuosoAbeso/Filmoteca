package es.ua.eps.filmoteca

object FilmDataSource {
    val films: MutableList<Film> = mutableListOf()

    init {
        val f1 = Film().apply {
            title = "Regreso al futuro"
            director = "Robert Zemeckis"
            imageResId = R.mipmap.ic_launcher
            comments = "Clásico de los 80"
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_SCIFI
            imdbUrl = "http://www.imdb.com/title/tt0088763"
            year = 1985
        }
        films.add(f1)

        val f2 = Film().apply {
            title = "Titanic"
            director = "James Cameron"
            imageResId = R.mipmap.ic_launcher
            comments = "Romance épico"
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_DRAMA
            imdbUrl = "http://www.imdb.com/title/tt0120338"
            year = 1997
        }
        films.add(f2)

        val f3 = Film().apply {
            title = "La La Land"
            director = "Damien Chazelle"
            imageResId = R.mipmap.ic_launcher
            comments = "Musical moderno"
            format = Film.FORMAT_DVD
            genre = Film.GENRE_COMEDY
            imdbUrl = "http://www.imdb.com/title/tt3783958"
            year = 2016
        }
        films.add(f3)

        // Puedes añadir más películas aquí...
    }
}
