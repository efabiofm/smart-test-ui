(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PruebaDetailController', PruebaDetailController);

    PruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Prueba', 'Ambiente', 'Modulo', 'Servicio', 'Metodo', 'EjecucionPrueba', 'PlanPrueba'];

    function PruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, Prueba, Ambiente, Modulo, Servicio, Metodo, EjecucionPrueba, PlanPrueba) {
        var vm = this;

        vm.prueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:pruebaUpdate', function(event, result) {
            vm.prueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
