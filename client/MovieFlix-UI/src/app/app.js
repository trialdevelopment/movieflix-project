(function(){
    'use strict';

    angular
        .module('movie-flix',['ui.router','ui.bootstrap','angularMoment','ngStorage','ngMessages','ngAnimate','ui-notification'])
        .config(moduleConfig);

    moduleConfig.$inject = ['$stateProvider', '$urlRouterProvider','NotificationProvider'];
    function moduleConfig($stateProvider, $urlRouterProvider,NotificationProvider) {

        $urlRouterProvider.otherwise('');

        $stateProvider

            .state('signin', {
                url: '',
                templateUrl: 'app/views/signin.tmpl.html',
                controller: 'UserController',
                controllerAs: 'userVm'
            })

            .state('movies', {
                url: '/movies',
                templateUrl: 'app/views/movies.tmpl.html',
                controller:'MovieController',
                controllerAs: 'movieVm'
            })

            .state('movie-details', {
                url: '/movie-details/:title',
                templateUrl: 'app/views/movie-details.tmpl.html',
                controller:'MovieDetailController',
                controllerAs: 'movieDetailVm'
            });


        NotificationProvider.setOptions({
            delay: 1200,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'center',
            positionY: 'top'
        });
    }
})();