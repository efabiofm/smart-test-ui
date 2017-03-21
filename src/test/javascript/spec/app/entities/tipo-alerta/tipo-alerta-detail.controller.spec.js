'use strict';

describe('Controller Tests', function() {

    describe('TipoAlerta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipoAlerta, MockAlerta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipoAlerta = jasmine.createSpy('MockTipoAlerta');
            MockAlerta = jasmine.createSpy('MockAlerta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TipoAlerta': MockTipoAlerta,
                'Alerta': MockAlerta
            };
            createController = function() {
                $injector.get('$controller')("TipoAlertaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:tipoAlertaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
