package cinema.TDA;

public class Movie {
    private String title;
    private String genre;
    private int durationMinutes;
    
    public Movie(String title, String genre, int durationMinutes){
        if (title == null || title.trim().isEmpty()|| durationMinutes <= 0){
            throw new IllegalArgumentException("Título o duración inválidos");
        }
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public int getDurationMinutes(){
        return durationMinutes;
    }
    
    public void setTitle(String title){
        if (title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        this.title = title;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public void setDurationMinutes(int durationMinutes){
        if (durationMinutes <= 0){
            throw new IllegalArgumentException("La duración debe ser mayor a 0");
        }
        this.durationMinutes = durationMinutes;
    }
    
    @Override
    public String toString(){
        return String.format("Película: %s (Género: %s, Duración: %d min)",
                title, genre, durationMinutes);
    }
}