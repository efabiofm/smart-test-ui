(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('MetodoDetailController', MetodoDetailController);

    MetodoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Metodo', 'Servicio', 'Prueba', 'Parametro'];

    function MetodoDetailController($scope, $rootScope, $stateParams, previousState, entity, Metodo, Servicio, Prueba, Parametro) {
        var vm = this;

        vm.metodo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:metodoUpdate', function(event, result) {
            vm.metodo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
