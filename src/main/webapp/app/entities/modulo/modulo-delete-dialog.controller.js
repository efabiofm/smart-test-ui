(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ModuloDeleteController',ModuloDeleteController);

    ModuloDeleteController.$inject = ['$uibModalInstance', 'entity', 'Modulo'];

    function ModuloDeleteController($uibModalInstance, entity, Modulo) {
        var vm = this;

        vm.modulo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Modulo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
