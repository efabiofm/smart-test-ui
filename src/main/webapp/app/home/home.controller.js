(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal'];

    function HomeController ($scope, Principal) {
        var vm = this;
        vm.ejecucion = {
          metodo: "POST"
        };

        vm.account = null;
        vm.isAuthenticated = null;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
