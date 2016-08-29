(function() {
    'use strict';

    angular.module('movie-flix')
        .controller('ModalController', ModalController);

    ModalController.$inject=['$uibModalInstance','movie','title','btnTxt','movieService','$filter','Notification']
    function ModalController($uibModalInstance,movie,title,btnTxt,movieService,$filter,Notification){

        var modalVm = this;

        modalVm.movie = movie;
        modalVm.title = title;
        modalVm.btnTxt = btnTxt;
        modalVm.close = close;
        modalVm.movieAlreadyExists = false;
        modalVm.movieNotFound = false;
        modalVm.format = 'dd MMM yyyy';
        modalVm.altInputFormats = ['M!/d!/yyyy'];
        modalVm.deleteMovie = deleteMovie;
        modalVm.updateMovie = updateMovie;
        modalVm.addMovie = addMovie;
        modalVm.clear = clear;
        modalVm.open = open;
        modalVm.popup = {
            opened: false
        };
        modalVm.dateOptions = {
            dateDisabled: false,
            formatYear: 'yyyy',
            maxDate: new Date(2026, 5, 22),
            minDate: new Date(1700, 5,22),
            startingDay: 1,
            showWeeks:false
        };

        released();
        function released() {
            if(modalVm.movie != null){
                modalVm.movie.released = new Date(modalVm.movie.released);
            }
        };

        function clear() {
            modalVm.movie.released = null;
        };

        function open() {
            modalVm.popup.opened = true;
        };

        function deleteMovie() {
            movieService
                .deleteMovie(modalVm.movie.movieID)
                .then(function (data){
                    Notification.success('Movie Deleted..');
                    close();
                },function(errorData) {
                    modalVm.movieNotFound = true;
                    Notification.error('Not Found!');
                });
        }

        function updateMovie(isValid) {

            if(isValid) {

                modalVm.movie.released = $filter('date')(modalVm.movie.released,'dd MMM yyyy');
                if(modalVm.btnTxt=="Update"){

                    movieService
                        .updateMovie(modalVm.movie.movieID, modalVm.movie)
                        .then(function (data) {
                            Notification.success('Movie Updated..');
                            close();
                        }, function (errorData) {
                            console.log(errorData);
                            if(errorData == 404) {
                                modalVm.movieNotFound = true;
                                Notification.error('Not Found!');
                            }
                            else {
                                modalVm.movieAlreadyExists = true;
                                Notification.error('Already Exists!');
                            }
                        });
                }
                else{
                    movieService
                        .addMovie(modalVm.movie)
                        .then(function (data) {
                            Notification.success('Movie Added Successfully..');
                            close();
                        }, function (errorData) {
                            Notification.error('Already Exists!');
                            modalVm.movieAlreadyExists = true;
                        });
                }
            }
        }

        function addMovie(isValid) {

            if(isValid) {
                modalVm.movie.released = $filter('date')(modalVm.movie.released, 'dd MMM yyyy');
                movieService
                    .addMovie(modalVm.movie)
                    .then(function (data) {
                        Notification.success('Movie Added Successfully..');
                        close();
                    }, function (errorData) {
                        modalVm.movieAlreadyExists = true;
                        Notification.error('Already Exists');
                    });
            }
        }

        function close(){
            $uibModalInstance.close();
        }
    }
})();