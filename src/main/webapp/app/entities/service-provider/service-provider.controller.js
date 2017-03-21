(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServiceProviderController', ServiceProviderController);

    ServiceProviderController.$inject = ['$scope', '$state', 'ServiceProvider'];

    function ServiceProviderController ($scope, $state, ServiceProvider) {
        var vm = this;

        vm.serviceProviders = [];

        loadAll();

        function loadAll() {
            ServiceProvider.query(function(result) {
                vm.serviceProviders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
