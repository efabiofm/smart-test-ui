(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PlanPruebaDeleteController',PlanPruebaDeleteController);

    PlanPruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'PlanPrueba'];

    function PlanPruebaDeleteController($uibModalInstance, entity, PlanPrueba) {
        var vm = this;

        vm.planPrueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PlanPrueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
