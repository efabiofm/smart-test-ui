(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServiceProviderDetailController', ServiceProviderDetailController);

    ServiceProviderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServiceProvider', 'Header'];

    function ServiceProviderDetailController($scope, $rootScope, $stateParams, previousState, entity, ServiceProvider, Header) {
        var vm = this;

        vm.serviceProvider = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:serviceProviderUpdate', function(event, result) {
            vm.serviceProvider = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
