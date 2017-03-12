(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PlanPruebaDetailController', PlanPruebaDetailController);

    PlanPruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PlanPrueba', 'Prueba'];

    function PlanPruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, PlanPrueba, Prueba) {
        var vm = this;

        vm.planPrueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:planPruebaUpdate', function(event, result) {
            vm.planPrueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
