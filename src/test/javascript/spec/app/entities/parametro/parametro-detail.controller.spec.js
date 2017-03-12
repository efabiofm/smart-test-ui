'use strict';

describe('Controller Tests', function() {

    describe('Parametro Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockParametro, MockTipoParametro, MockMetodo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockParametro = jasmine.createSpy('MockParametro');
            MockTipoParametro = jasmine.createSpy('MockTipoParametro');
            MockMetodo = jasmine.createSpy('MockMetodo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Parametro': MockParametro,
                'TipoParametro': MockTipoParametro,
                'Metodo': MockMetodo
            };
            createController = function() {
                $injector.get('$controller')("ParametroDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:parametroUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
