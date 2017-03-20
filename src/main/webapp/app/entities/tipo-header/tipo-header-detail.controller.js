(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoHeaderDetailController', TipoHeaderDetailController);

    TipoHeaderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoHeader', 'Header'];

    function TipoHeaderDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoHeader, Header) {
        var vm = this;

        vm.tipoHeader = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:tipoHeaderUpdate', function(event, result) {
            vm.tipoHeader = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
