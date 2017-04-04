(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('EjecucionPruebaDeleteController',EjecucionPruebaDeleteController);

    EjecucionPruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'EjecucionPrueba'];

    function EjecucionPruebaDeleteController($uibModalInstance, entity, EjecucionPrueba) {
        var vm = this;

        vm.ejecucionPrueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EjecucionPrueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
