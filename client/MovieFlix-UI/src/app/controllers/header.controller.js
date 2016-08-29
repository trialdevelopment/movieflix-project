(function() {
    'use strict';

    angular
        .module('movie-flix')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['$rootScope','$sessionStorage','$state', 'Notification'];
    function HeaderController($rootScope,$sessionStorage,$state,Notification) {

        var headerVm = this;

        $rootScope.signOutFlag = false;
        headerVm.signOut = signOut;
        headerVm.home = home;

        function home() {
            $state.go("movies");
        }

        function signOut(){
            delete $sessionStorage.user;
            $state.go("signin");
            Notification.success('Logged Out..');
        }
    }
})();