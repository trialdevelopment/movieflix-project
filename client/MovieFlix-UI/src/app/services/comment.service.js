(function(){
    'use strict';

    angular
        .module('movie-flix')
        .service('commentService',commentService);

    commentService.$inject = ['$http','$q','CONFIG'];
    function commentService($http,$q,CONFIG){

        var self = this;

        self.getComments = getComments;
        self.addComment = addComment;

        function getComments(){
            return $http.get(CONFIG.API_END_POINT + '/comments')
                .then(successFn,errorFn);
        }

        function addComment(comment){
            return $http.post(CONFIG.API_END_POINT + '/comments/',comment)
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