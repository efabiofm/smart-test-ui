(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PlanPruebaController', PlanPruebaController);

    PlanPruebaController.$inject = ['$scope', '$state', 'PlanPrueba'];

    function PlanPruebaController ($scope, $state, PlanPrueba) {
        var vm = this;

        vm.planPruebas = [];

        loadAll();

        function loadAll() {
            PlanPrueba.query(function(result) {
                vm.planPruebas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
