//  데이터의 추가

```java
ArrayList<MovieVO> data = new ArrayList<>();
MovieVO movie = new MovieVO();
movie.image = R.drawable.toystory;
movie.title = "토이스토리";
movie.year = "1995";

data.add(movie);

movie = new MovieVO();
movie.image = R.drawable.toystory_2;
movie.title = "토이스토리 2";
movie.year = "1999";

data.add(movie);

movie = new MovieVO();
movie.image = R.drawable.toystory_3;
movie.title = "토이스토리 3";
movie.year = "2010";

data.add(movie);

movie = new MovieVO();
movie.image = R.drawable.toystory_4;
movie.title = "토이스토리 4";
movie.year = "2018";

data.add(movie);
```