(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PlanPruebaController', PlanPruebaController);

    PlanPruebaController.$inject = ['$scope', '$state', 'PlanPrueba'];

    function PlanPruebaController ($scope, $state, PlanPrueba) {
        var vm = this;
        vm.ejecutarPrueba = ejecutarPrueba;

        vm.planPruebas = [];

        loadAll();

        function loadAll() {
            PlanPrueba.query(function(result) {
                vm.planPruebas = result;
                vm.searchQuery = null;
            });
        }

        function ejecutarPrueba(id) {
            PlanPrueba.ejecutarPrueba({id:id}).$promise.then(function(){
                $state.go("ejecucion-prueba");
            });
        }

    }
})();
