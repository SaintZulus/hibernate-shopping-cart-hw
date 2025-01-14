package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.ShoppingCartService;

public class Main {
    private static Injector instance = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException {
        //create movie + add
        MovieService movieService = (MovieService) instance.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        //create CinemaHall + add
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");
        CinemaHallService cinemaHallService = (CinemaHallService)
                instance.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        //create moviesession + add
        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));
        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));
        MovieSessionService movieSessionService = (MovieSessionService)
                instance.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);
        //create user
        AuthenticationService authenticationService = (AuthenticationService)
                instance.getInstance(AuthenticationService.class);
        User qwertyui = authenticationService.register("sdfa@df.com", "Qwertyui");
        ShoppingCartService shoppingCartService = (ShoppingCartService)
                instance.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(yesterdayMovieSession,qwertyui);
        //shoppingCartService.clear(qwertyu.);
        //System.out.println(shoppingCartService.getByUser(qwertyui));
    }
}
