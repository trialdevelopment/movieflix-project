(function(){
    'use strict';

    angular
        .module('movie-flix')
        .controller('MovieDetailController',MovieDetailController);

    MovieDetailController.$inject = ['$rootScope','$state','$stateParams','movieService','commentService','$sessionStorage', 'Notification']
    function MovieDetailController($rootScope,$state,$stateParams,movieService,commentService,$sessionStorage, Notification){
        var movieDetailVm = this;
        $rootScope.signOutFlag = true;
        movieDetailVm.title = $stateParams.title;
        movieDetailVm.user = $sessionStorage.user;
        movieDetailVm.updateRating = updateRating;
        movieDetailVm.commentsFlag = true;
        movieDetailVm.showComments = showComments;
        movieDetailVm.addComment = addComment;
        loginCheck();

        function loginCheck() {
            if(movieDetailVm.user == null) {
                $state.go("signin");
            }
            else {
                init();
            }
        }

        function init() {
            movieService
                .getMovie(movieDetailVm.title)
                .then(function (data) {
                    movieDetailVm.movie = data;
                    displayComments();
                }, function (errorData) {
                    console.log(errorData);
                    $state.go("movies");
                    Notification.error('Movie Not Found');
                    //alert("Movie Not Found");
                });
        }

        function displayComments() {
            commentService
                .getComments()
                .then(function (data) {
                    movieDetailVm.comments = data;
                }, function (errorData) {
                    Notification.error('Error..!');
                    console.log(errorData);
                });
        }

        function addComment(){
            movieDetailVm.newComment.movie = movieDetailVm.movie;
            movieDetailVm.newComment.user = movieDetailVm.user;
            movieDetailVm.newComment.timestamp = new Date();
            commentService
                .addComment(movieDetailVm.newComment)
                .then(function successFn(data){
                        Notification.success('Comment Added Successfully..');
                        movieDetailVm.newComment = null;
                        displayComments()
                    },
                    function errorFn(error){
                        Notification.error('Error..!');
                        console.log(error);
                    });
        }

        function updateRating(){
            movieService
                .updateRating(movieDetailVm.movie.movieID,movieDetailVm.movie.rating)
                .then(function (data) {
                        Notification.success('Rating Recorded Successfully..');
                        displayComments();
                        $state.reload();
                    },
                    function (errorData) {
                        Notification.error('Error..!');
                        console.log(errorData);
                    });
        }

        function showComments(){
            movieDetailVm.commentsFlag =! movieDetailVm.commentsFlag;
        }
    }
})();