(function (){
    'use strict';

    angular
        .module('movie-flix')
        .service('userService',userService);

    userService.$inject = ['$http','$q','CONFIG'];
    function userService($http,$q,CONFIG){
        var self = this;

        self.signIn = signIn;
        self.signUp = signUp;

        function signIn(email,pwd){
            return $http.get(CONFIG.API_END_POINT + '/users/'+email+'/'+pwd)
                .then(successFn,errorFn);
        }

        function signUp(user){
            return $http.post(CONFIG.API_END_POINT + '/users/',user)
                .then(successFn,errorFn);
        }

        function successFn(response) {
            return response.data;
        }

        function errorFn(response) {
            return $q.reject(response.status);
        }
    }
})();