(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServicioDetailController', ServicioDetailController);

    ServicioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Servicio', 'Metodo', 'Prueba', 'Modulo'];

    function ServicioDetailController($scope, $rootScope, $stateParams, previousState, entity, Servicio, Metodo, Prueba, Modulo) {
        var vm = this;

        vm.servicio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:servicioUpdate', function(event, result) {
            vm.servicio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
