(function(){
    'use strict';

    angular.module('movie-flix')
        .controller('MovieController',MovieController);

    MovieController.$inject = ['$rootScope','movieService','$state','$sessionStorage','$uibModal','Notification'];
    function MovieController($rootScope,movieService,$state,$sessionStorage,$uibModal,Notification){

        var movieVm = this;

        $rootScope.signOutFlag = true;

        movieVm.sort = sort;
        movieVm.sortOrderChange = sortOrderChange;

        movieVm.user = $sessionStorage.user;
        movieVm.loginCheck = loginCheck;
        movieVm.itemsPerPage = 8;
        movieVm.pageChanged = pageChanged;
        movieVm.deleteFlag = false;
        movieVm.updateFlag = false;
        movieVm.addFlag = false;
        movieVm.showModal = false;
        movieVm.showAdminPriveleges = showAdminPriveleges;
        movieVm.deleteMovie = deleteMovie;
        movieVm.updateMovie = updateMovie;
        movieVm.addMovie = addMovie;

        loginCheck();

        function loginCheck() {
            if(movieVm.user == null) {
                $state.go("signin");
            }
            else {
                init();
            }
        }

        function init(){

            movieService
                .getMovies()
                .then(function (data){
                    movieVm.movies = data;
                    movieVm.length = data.length;
                    movieVm.currentPage = 1;
                    showAdminPriveleges();
                },function(errorData) {
                    console.log(errorData);
                });
        }

        function showAdminPriveleges(){
            if(movieVm.user.role === "ADMIN"){
                movieVm.deleteFlag = true;
                movieVm.addFlag = true;
                movieVm.updateFlag = true;
                movieVm.itemsPerPage = 7;
            }
        }

        function pageChanged() {
            if(movieVm.currentPage == 1){
                if(movieVm.user.role === "ADMIN"){
                    movieVm.addFlag = true;
                    movieVm.itemsPerPage = 7;
                }
            }
            else {
                movieVm.itemsPerPage = 8;
                movieVm.addFlag = false;
            }
        }

        function sort(keyname){
            movieVm.sortKey=keyname;
            movieVm.reverse = false;
        }

        function sortOrderChange(){
            movieVm.reverse=!movieVm.reverse;
        }

        function deleteMovie(movie){

            var modalInstance = $uibModal.open({
                templateUrl: 'app/views/delete-movie.tmpl.html',
                controller: 'ModalController as modalVm',
                resolve: {
                    movie: function(){
                        return movie;
                    },
                    title: function(){
                        return "Delete Movie";
                    },
                    btnTxt: function(){
                        return "Delete";
                    }
                }
            });

            modalInstance.result.then(function(){
                init();
            });
        }

        function updateMovie(movie){

            var modalInstance = $uibModal.open({
                templateUrl: 'app/views/update-movie.tmpl.html',
                controller: 'ModalController as modalVm',
                resolve: {
                    movie: function(){
                        return movie;
                    },
                    title: function(){
                        return "Update Movie";
                    },
                    btnTxt: function(){
                        return "Update";
                    }
                }
            });

            modalInstance.result.then(function(){
                init();
            });
        }

        function addMovie(movie){

            var modalInstance = $uibModal.open({
                templateUrl: 'app/views/update-movie.tmpl.html',
                controller: 'ModalController as modalVm',
                resolve: {
                    movie: function(){
                        return movie;
                    },
                    title: function(){
                        return "Add a Movie";
                    },
                    btnTxt: function(){
                        return "Add";
                    }
                }
            });

            modalInstance.result.then(function(){
                init();
            });
        }
    }

})();