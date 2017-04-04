(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoEventoDialogController', TipoEventoDialogController);

    TipoEventoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoEvento', 'Bitacora'];

    function TipoEventoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoEvento, Bitacora) {
        var vm = this;

        vm.tipoEvento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bitacoras = Bitacora.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoEvento.id !== null) {
                TipoEvento.update(vm.tipoEvento, onSaveSuccess, onSaveError);
            } else {
                TipoEvento.save(vm.tipoEvento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:tipoEventoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
