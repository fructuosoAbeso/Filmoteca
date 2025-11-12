
package es.ua.eps.filmoteca

object FilmDataSource {
    val films: MutableList<Film> = mutableListOf()

    init {
        val f1 = Film().apply {
            title = "Regreso al Futuro"
            director = "Robert Zemeckis"
            imageResId = R.drawable.regreso_al_futuro
            comments = "Clásico de los 80 con viajes en el tiempo."
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt0088763/"
            year = 1985
        }
        films.add(f1)

        val f2 = Film().apply {
            title = "Titanic"
            director = "James Cameron"
            imageResId = R.drawable.titanic
            comments = "Una historia de amor en medio del desastre."
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0120338/"
            year = 1997
        }
        films.add(f2)

        val f3 = Film().apply {
            title = "La La Land"
            director = "Damien Chazelle"
            imageResId = R.drawable.la_la_land
            comments = "Un homenaje moderno a los musicales clásicos."
            format = Film.FORMAT_DVD
            genre = Film.GENRE_COMEDY
            imdbUrl = "https://www.imdb.com/title/tt3783958/"
            year = 2016
        }
        films.add(f3)

        val f4 = Film().apply {
            title = "Avatar"
            director = "James Cameron"
            imageResId = R.drawable.avatar
            comments = "Una aventura visual en el planeta Pandora."
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt0499549/"
            year = 2009
        }
        films.add(f4)

        val f6 = Film().apply {
            title = "El Padrino"
            director = "Francis Ford Coppola"
            imageResId = R.drawable.el_padrino
            comments = "Una obra maestra del cine de mafias."
            format = Film.FORMAT_DVD
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0068646/"
            year = 1972
        }
        films.add(f6)

        val f7 = Film().apply {
            title = "Forrest Gump"
            director = "Robert Zemeckis"
            imageResId = R.drawable.forrest_gump
            comments = "La vida es como una caja de chocolates..."
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0109830/"
            year = 1994
        }
        films.add(f7)

        val f10 = Film().apply {
            title = "Matrix"
            director = "Lana Wachowski, Lilly Wachowski"
            imageResId = R.drawable.the_matrix
            comments = "La realidad nunca fue lo que parece."
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt0133093/"
            year = 1999
        }
        films.add(f10)


    }
}
