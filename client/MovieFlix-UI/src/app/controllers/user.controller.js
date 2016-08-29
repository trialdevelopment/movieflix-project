(function(){
    'use strict';

    angular
        .module('movie-flix')
        .controller('UserController',UserController);

    UserController.$inject = ['$rootScope','userService','$state','$sessionStorage', 'Notification'];
    function UserController($rootScope,userService,$state,$sessionStorage,Notification){

        var userVm = this;

        $rootScope.signOutFlag = false;
        userVm.userNotFound = false;
        userVm.userAlreadyExists = false;
        userVm.signIn = signIn;
        userVm.signUp = signUp;

        delete $sessionStorage.user;

        function signIn(isValid){

            if(isValid) {
                userService
                    .signIn(userVm.email, userVm.password)
                    .then(function (data) {
                            userVm.$storage = $sessionStorage.$default({
                                user: data
                            });
                            $state.go("movies");
                            Notification.success('Login Successful..');
                        },
                        function (error) {
                            userVm.userNotFound = true;
                            Notification.error('INVALID!');
                        })
            }
        }

        function signUp(isValid){

            if(isValid){
                userVm.newUser.role="USER";
                userService
                    .signUp(userVm.newUser)
                    .then(function successFn(data){
                            userVm.newUser = null;
                            userVm.$storage = $sessionStorage.$default({
                                user: data
                            });
                            $state.go("movies");
                            Notification.success('Sign-up Successful..');
                        },
                        function errorFn(error){
                            userVm.userAlreadyExists = true;
                            Notification.error('Sign-up Failed..');
                        });
            }
        }
    }

})();