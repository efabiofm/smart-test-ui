(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoHeaderDeleteController',TipoHeaderDeleteController);

    TipoHeaderDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoHeader'];

    function TipoHeaderDeleteController($uibModalInstance, entity, TipoHeader) {
        var vm = this;

        vm.tipoHeader = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoHeader.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
