(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HeaderDetailController', HeaderDetailController);

    HeaderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Header', 'TipoHeader', 'ServiceProvider'];

    function HeaderDetailController($scope, $rootScope, $stateParams, previousState, entity, Header, TipoHeader, ServiceProvider) {
        var vm = this;

        vm.header = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:headerUpdate', function(event, result) {
            vm.header = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
