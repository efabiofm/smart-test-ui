(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoEventoDeleteController',TipoEventoDeleteController);

    TipoEventoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoEvento'];

    function TipoEventoDeleteController($uibModalInstance, entity, TipoEvento) {
        var vm = this;

        vm.tipoEvento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoEvento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
