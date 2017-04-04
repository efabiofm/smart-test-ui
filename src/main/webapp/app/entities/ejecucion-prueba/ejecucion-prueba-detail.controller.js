(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('EjecucionPruebaDetailController', EjecucionPruebaDetailController);

    EjecucionPruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EjecucionPrueba', 'Prueba'];

    function EjecucionPruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, EjecucionPrueba, Prueba) {
        var vm = this;

        vm.ejecucionPrueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:ejecucionPruebaUpdate', function(event, result) {
            vm.ejecucionPrueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
