package seclibrary.security



import org.junit.*
import grails.test.mixin.*

@TestFor(RequestmapController)
@Mock(Requestmap)
class RequestmapControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/requestmap/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.requestmapInstanceList.size() == 0
        assert model.requestmapInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.requestmapInstance != null
    }

    void testSave() {
        controller.save()

        assert model.requestmapInstance != null
        assert view == '/requestmap/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/requestmap/show/1'
        assert controller.flash.message != null
        assert Requestmap.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/requestmap/list'


        populateValidParams(params)
        def requestmap = new Requestmap(params)

        assert requestmap.save() != null

        params.id = requestmap.id

        def model = controller.show()

        assert model.requestmapInstance == requestmap
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/requestmap/list'


        populateValidParams(params)
        def requestmap = new Requestmap(params)

        assert requestmap.save() != null

        params.id = requestmap.id

        def model = controller.edit()

        assert model.requestmapInstance == requestmap
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/requestmap/list'

        response.reset()


        populateValidParams(params)
        def requestmap = new Requestmap(params)

        assert requestmap.save() != null

        // test invalid parameters in update
        params.id = requestmap.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/requestmap/edit"
        assert model.requestmapInstance != null

        requestmap.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/requestmap/show/$requestmap.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        requestmap.clearErrors()

        populateValidParams(params)
        params.id = requestmap.id
        params.version = -1
        controller.update()

        assert view == "/requestmap/edit"
        assert model.requestmapInstance != null
        assert model.requestmapInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/requestmap/list'

        response.reset()

        populateValidParams(params)
        def requestmap = new Requestmap(params)

        assert requestmap.save() != null
        assert Requestmap.count() == 1

        params.id = requestmap.id

        controller.delete()

        assert Requestmap.count() == 0
        assert Requestmap.get(requestmap.id) == null
        assert response.redirectedUrl == '/requestmap/list'
    }
}
