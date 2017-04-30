(function() {
    'use strict';
    /* globals SockJS, Stomp */

    angular
        .module('smartTestUiApp')
        .factory('JhiTestsService', JhiTestsService);

    JhiTestsService.$inject = ['$rootScope', '$window', '$cookies', '$http', '$q', 'AuthServerProvider'];

    function JhiTestsService ($rootScope, $window, $cookies, $http, $q, AuthServerProvider) {
        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();
        var alreadyConnectedOnce = false;

        var service = {
            connect: connect,
            disconnect: disconnect,
            receive: receive,
            subscribe: subscribe,
            unsubscribe: unsubscribe
        };

        return service;

        function connect () {
            //building absolute path so that websocket doesn't fail when deploying with a context path
            var loc = $window.location;
            var url = '//' + loc.host + loc.pathname + 'websocket/tests';
            var authToken = AuthServerProvider.getToken();
            if(authToken){
                url += '?access_token=' + authToken;
            }

            console.log(url)

            var socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            var stateChangeStart;
            var headers = {};
            stompClient.connect(headers, function() {
                connected.resolve('success');
            });
            $rootScope.$on('$destroy', function () {
                if(angular.isDefined(stateChangeStart) && stateChangeStart !== null){
                    stateChangeStart();
                }
            });
        }

        function disconnect () {
            if (stompClient !== null) {
                stompClient.disconnect();
                stompClient = null;
            }
        }

        function receive () {
            return listener.promise;
        }

        function subscribe () {
            console.log("susasdasd")
            connected.promise.then(function() {
                subscriber = stompClient.subscribe('/topic/tests', function(data) {
                    listener.notify(angular.fromJson(data.body));
                });
            }, null, null);
        }

        function unsubscribe () {
            if (subscriber !== null) {
                subscriber.unsubscribe();
            }
            listener = $q.defer();
        }
    }
})();
