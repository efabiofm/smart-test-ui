(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('SeguridadDeleteController',SeguridadDeleteController);

    SeguridadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seguridad'];

    function SeguridadDeleteController($uibModalInstance, entity, Seguridad) {
        var vm = this;

        vm.seguridad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seguridad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
