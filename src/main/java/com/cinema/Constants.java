package com.cinema;

import lombok.Data;

@Data
public class Constants {

    //Admins Servlet
    public static final String ADMIN_DELETE_MOVIE_BY_ID_URI_SFX = "deletebyid";
    public static final String ADMIN_DELETE_MOVIE_CHECKBOX_GROUP = "deleteaction";
    public static final String ADMIN_ADD_MOVIE_URI_SFX = "add";
    public static final String ADMIN_UPDATE_MOVIE_URI_SFX = "update";
    public static final String FORM_PARAM_MOVIE_ID = "id";
    public static final String FORM_PARAM_MOVIE_TITLE = "title";
    public static final String FORM_PARAM_MOVIE_DESCRIPTION = "description";
    public static final String FORM_PARAM_MOVIE_DURATION = "duration";


    /* EL Accessors */
    //Admins Servlet
    String adminDeleteMovieCheckboxGroup = ADMIN_DELETE_MOVIE_CHECKBOX_GROUP;
    String adminDeleteMovieByIdUriSfx = ADMIN_DELETE_MOVIE_BY_ID_URI_SFX;
    String adminAddMovieUriSfx = ADMIN_ADD_MOVIE_URI_SFX;
    String adminUpdateMovieUriSfx = ADMIN_UPDATE_MOVIE_URI_SFX;
    String formParamMovieId = FORM_PARAM_MOVIE_ID;
    String formParamMovieTitle = FORM_PARAM_MOVIE_TITLE;
    String formParamMovieDescription = FORM_PARAM_MOVIE_DESCRIPTION;
    String formParamMovieDuration = FORM_PARAM_MOVIE_DURATION;


    //Users Servlet
    public static final String ADMIN_DELETE_USER_BY_ID_URI_SFX = "deletebyid";
    public static final String ADMIN_DELETE_USER_CHECKBOX_GROUP = "deleteaction";
    public static final String ADMIN_ADD_USER_URI_SFX = "add";
    public static final String ADMIN_UPDATE_USER_URI_SFX = "update";
    public static final String FORM_PARAM_USER_ID = "id";
    public static final String FORM_PARAM_USER_LOGIN = "login";
    public static final String FORM_PARAM_USER_PASSWORD = "password";
    public static final String FORM_PARAM_USER_FIRSTNAME = "firstname";
    public static final String FORM_PARAM_USER_LASTNAME = "lastname";
    public static final String FORM_PARAM_USER_ROLE = "role";
    public static final String FORM_PARAM_USER_BIRTHDATE = "birthdate";
    public static final String FORM_PARAM_USER_EMAIL = "email";


    /* EL Accessors */
    //Users Servlet
    String adminDeleteUserCheckboxGroup = ADMIN_DELETE_USER_CHECKBOX_GROUP;
    String adminDeleteUserByIdUriSfx = ADMIN_DELETE_USER_BY_ID_URI_SFX;
    String adminAddUserUriSfx = ADMIN_ADD_USER_URI_SFX;
    String adminUpdateUserUriSfx = ADMIN_UPDATE_USER_URI_SFX;
    String formParamUserId = FORM_PARAM_USER_ID;
    String formParamUserLogin = FORM_PARAM_USER_LOGIN;
    String formParamUserPassword = FORM_PARAM_USER_PASSWORD;
    String formParamUserFirstname = FORM_PARAM_USER_FIRSTNAME;
    String formParamUserLastname = FORM_PARAM_USER_LASTNAME;
    String formParamUserRole = FORM_PARAM_USER_ROLE;
    String formParamUserBirthdate = FORM_PARAM_USER_BIRTHDATE;
    String formParamUserEmail = FORM_PARAM_USER_EMAIL;


    /* Tickets */

    public static final String ADMIN_LIST_TICKETS_BY_USER_ID = "listbyuserid";
    public static final String USER_RETURN_TICKETS_CHECKBOX = "returnTicketsCheckbox";
    public static final String USER_RETURN_TICKETS_URI_SFX = "returntickets";

    String adminListTicketsByUserId = ADMIN_LIST_TICKETS_BY_USER_ID;
    String userReturnTicketsCheckbox = USER_RETURN_TICKETS_CHECKBOX;
    String userReturnTicketsUriSfx = USER_RETURN_TICKETS_URI_SFX;



    /* Sessions */

    public static final String ADMIN_DELETE_SESSION_BY_ID_URI_SFX = "deletebyid";
    public static final String ADMIN_DELETE_SESSION_CHECKBOX_GROUP = "deleteaction";
    public static final String ADMIN_UPDATE_SESSION_URI_SFX = "update";
    public static final String ADMIN_ADD_SESSION_URI_SFX = "add";
    public static final String FORM_PARAM_SESSION_ID = "id";
    public static final String FORM_PARAM_SESSION_MOVIE_ID = "movie_id";
    public static final String FORM_PARAM_SESSION_HALL_ID = "hall_id";
    public static final String FORM_PARAM_SESSION_DATETIME = "datetime";

    String adminDeleteSessionByIdUriSfx = ADMIN_DELETE_SESSION_BY_ID_URI_SFX;
    String adminUpdateSessionUriSfx = ADMIN_UPDATE_SESSION_URI_SFX;
    String adminDeleteSessionCheckboxGroup = ADMIN_DELETE_SESSION_CHECKBOX_GROUP;
    String adminAddSessionUriSfx = ADMIN_ADD_USER_URI_SFX;

    String formParamSessionMovieId = FORM_PARAM_SESSION_MOVIE_ID;
    String formParamSessionHallId = FORM_PARAM_SESSION_HALL_ID;
    String formParamSessionDateTime = FORM_PARAM_SESSION_DATETIME;
    String getFormParamSessionId = FORM_PARAM_SESSION_ID;


    /* User Movie Sessions  */

    public static final String USER_MOVIE_SESSIONS_URI_SFX = "session_by_movie_id";

    String userMovieSessionsUriSfx = USER_MOVIE_SESSIONS_URI_SFX;

    public static final String USER_SESSION_URI_SFX = "session_by_id";

    String userSessionUriSfx = USER_SESSION_URI_SFX;

    /* User Tickets */
    public static final String BUY_TICKET_ON_SEESION_URI_SFX = "buy_ticket";
    public static final String BUY_TICKET_CHECKBOX = "tickets_for_session";

    String buyTicketOnSessionUriSfx = BUY_TICKET_ON_SEESION_URI_SFX;
    String buyTicketCheckbox = BUY_TICKET_CHECKBOX;

}
