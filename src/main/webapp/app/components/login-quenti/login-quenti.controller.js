(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('LoginQuentiController', LoginQuentiController);

    LoginQuentiController.$inject = ['$rootScope', '$state', '$timeout', 'Auth'];

    function LoginQuentiController ($rootScope, $state, $timeout, Auth) {
        var vm = this;

        vm.authenticationError = false;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.rememberMe = true;
        vm.username = null;
        vm.userClientId = "Hopware";

        $timeout(function (){angular.element('#username').focus();});

        function login (event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                userClientId: vm.userClientId,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $state.go('home-quenti');
                $rootScope.$broadcast('authenticationSuccess');
            }).catch(function () {
                vm.authenticationError = true;
            });
        }
    }
})();
