(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AmbienteDetailController', AmbienteDetailController);

    AmbienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ambiente', 'Prueba', 'Modulo'];

    function AmbienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Ambiente, Prueba, Modulo) {
        var vm = this;

        vm.ambiente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:ambienteUpdate', function(event, result) {
            vm.ambiente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
