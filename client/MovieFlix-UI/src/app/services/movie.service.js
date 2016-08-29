(function(){
    'use strict';

    angular
        .module('movie-flix')
        .service('movieService',movieService);

    movieService.$inject = ['$http','$q','CONFIG'];
    function movieService($http,$q,CONFIG){

        var self = this;

        self.getMovies = getMovies;
        self.deleteMovie = deleteMovie;
        self.updateMovie = updateMovie;
        self.addMovie = addMovie;
        self.getMovie = getMovie;
        self.updateRating = updateRating;

        function getMovies(){
            return $http.get(CONFIG.API_END_POINT + '/movies')
                .then(successFn,errorFn);
        }

        function deleteMovie(movieID){
            return $http.delete(CONFIG.API_END_POINT + '/movies/'+movieID)
                .then(successFn,errorFn);
        }

        function updateMovie(movieID,movie){
            return $http.put(CONFIG.API_END_POINT + '/movies/'+movieID, movie)
                .then(successFn,errorFn);
        }

        function getMovie(title) {
            return $http.get(CONFIG.API_END_POINT + '/movies/'+title)
                .then(successFn,errorFn);
        }

        function updateRating(id,rating) {
            return $http.put(CONFIG.API_END_POINT + '/movies/'+id+'/'+rating)
                .then(successFn,errorFn);
        }

        function addMovie(movie){
            return $http.post(CONFIG.API_END_POINT + '/movies/', movie)
                .then(successFn,errorFn);
        }

        function successFn(response){
            return response.data;
        }

        function errorFn(errResponse){
            return $q.reject(errResponse.status);
        }
    }
})();