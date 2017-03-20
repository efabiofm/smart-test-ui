(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('BitacoraDeleteController',BitacoraDeleteController);

    BitacoraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bitacora'];

    function BitacoraDeleteController($uibModalInstance, entity, Bitacora) {
        var vm = this;

        vm.bitacora = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bitacora.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
