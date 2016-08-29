(function() {
    'use strict';

    angular
        .module('movie-flix')
        .constant('CONFIG', {
            API_END_POINT: 'http://localhost:8080/movieflix/api'
        });
})();