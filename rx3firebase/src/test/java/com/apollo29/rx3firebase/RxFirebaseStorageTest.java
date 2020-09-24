package com.apollo29.rx3firebase;


import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.InputStream;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxFirebaseStorageTest {

    @Mock
    private StorageReference mockStorageRef;

    @Mock
    private Task<Void> mockVoidTask;

    @Mock
    private Task<byte[]> mockBytesTask;

    @Mock
    private Task<Uri> mockUriTask;

    @Mock
    private FileDownloadTask mockFileDownloadTask;

    @Mock
    private StreamDownloadTask mockStreamDownloadTask;

    @Mock
    private Task<StorageMetadata> mockMetadataTask;

    @Mock
    private UploadTask mockUploadTask;

    @Mock
    private Uri uri;

    @Mock
    private File file;

    @Mock
    private StorageMetadata metadata;

    @Mock
    private FileDownloadTask.TaskSnapshot fileSnapshot;

    @Mock
    private StreamDownloadTask.TaskSnapshot streamSnapshot;

    @Mock
    private UploadTask.TaskSnapshot uploadSnapshot;

    private byte[] nullBytes;

    private byte[] notNullbytes = new byte[0];

    @Mock
    private StreamDownloadTask.StreamProcessor processor;

    @Mock
    private InputStream stream;

    private Void voidData = null;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        RxTestUtil.setupTask(mockBytesTask);
        RxTestUtil.setupTask(mockVoidTask);
        RxTestUtil.setupTask(mockUriTask);
        RxTestUtil.setupTask(mockFileDownloadTask);
        RxTestUtil.setupTask(mockStreamDownloadTask);
        RxTestUtil.setupTask(mockMetadataTask);
        RxTestUtil.setupTask(mockUploadTask);

        when(mockStorageRef.getBytes(20)).thenReturn(mockBytesTask);
        when(mockStorageRef.getDownloadUrl()).thenReturn(mockUriTask);
        when(mockStorageRef.getFile(file)).thenReturn(mockFileDownloadTask);
        when(mockStorageRef.getFile(uri)).thenReturn(mockFileDownloadTask);
        when(mockStorageRef.getStream()).thenReturn(mockStreamDownloadTask);
        when(mockStorageRef.getStream(processor)).thenReturn(mockStreamDownloadTask);
        when(mockStorageRef.getMetadata()).thenReturn(mockMetadataTask);
        when(mockStorageRef.putBytes(notNullbytes)).thenReturn(mockUploadTask);
        when(mockStorageRef.putBytes(nullBytes)).thenReturn(mockUploadTask);
        when(mockStorageRef.putBytes(notNullbytes, metadata)).thenReturn(mockUploadTask);
        when(mockStorageRef.putBytes(nullBytes, metadata)).thenReturn(mockUploadTask);
        when(mockStorageRef.putFile(uri)).thenReturn(mockUploadTask);
        when(mockStorageRef.putFile(uri, metadata)).thenReturn(mockUploadTask);
        when(mockStorageRef.putFile(uri, metadata, uri)).thenReturn(mockUploadTask);
        when(mockStorageRef.putStream(stream)).thenReturn(mockUploadTask);
        when(mockStorageRef.putStream(stream, metadata)).thenReturn(mockUploadTask);
        when(mockStorageRef.updateMetadata(metadata)).thenReturn(mockMetadataTask);
        when(mockStorageRef.delete()).thenReturn(mockVoidTask);
    }

    @Test
    public void getBytes() {
        TestObserver<byte[]> storageTestObserver =
                RxFirebaseStorage.getBytes(mockStorageRef, 20)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(notNullbytes);
        RxTestUtil.testOnCompleteListener.getValue().onComplete(mockBytesTask);

        verify(mockStorageRef).getBytes(20);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(notNullbytes))
                .assertComplete()
                .dispose();
    }

    @Test
    public void getBytesNoData() {
        TestObserver<byte[]> storageTestObserver =
                RxFirebaseStorage.getBytes(mockStorageRef, 20)
                        .test();

        RxTestUtil.testOnFailureListener.getValue().onFailure(RxTestUtil.NULL_FIREBASE_EXCEPTION);

        verify(mockStorageRef).getBytes(20);

        storageTestObserver.assertError(RxTestUtil.NULL_FIREBASE_EXCEPTION)
                //.assertValueSet(Collections.singletonList(nullBytes))
                .assertNotComplete()
                .dispose();
    }

    @Test
    public void getDownloadUrl() {
        TestObserver<Uri> storageTestObserver =
                RxFirebaseStorage.getDownloadUrl(mockStorageRef)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uri);
        RxTestUtil.testOnCompleteListener.getValue().onComplete(mockUriTask);

        verify(mockStorageRef).getDownloadUrl();

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uri))
                .assertComplete()
                .dispose();
    }

    @Test
    public void getFile() {

        TestObserver<FileDownloadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.getFile(mockStorageRef, file)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(fileSnapshot);

        verify(mockStorageRef).getFile(file);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(fileSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void getFileUri() {

        TestObserver<FileDownloadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.getFile(mockStorageRef, uri)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(fileSnapshot);

        verify(mockStorageRef).getFile(uri);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(fileSnapshot))
                .assertComplete()
                .dispose();
    }


    @Test
    public void getMetadata() {

        TestObserver<StorageMetadata> storageTestObserver =
                RxFirebaseStorage.getMetadata(mockStorageRef)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(metadata);
        RxTestUtil.testOnCompleteListener.getValue().onComplete(mockMetadataTask);

        verify(mockStorageRef).getMetadata();

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(metadata))
                .assertComplete()
                .dispose();
    }


    @Test
    public void getStream() {

        TestObserver<StreamDownloadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.getStream(mockStorageRef)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(streamSnapshot);

        verify(mockStorageRef).getStream();

        storageTestObserver.assertNoErrors();
        storageTestObserver.assertValueCount(1);
        //storageTestObserver.assertValueSet(Collections.singletonList(streamSnapshot));
        storageTestObserver.assertComplete();
        storageTestObserver.dispose();
    }

    @Test
    public void getStreamProcessor() {

        TestObserver<StreamDownloadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.getStream(mockStorageRef, processor)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(streamSnapshot);

        verify(mockStorageRef).getStream(processor);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(streamSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putBytes() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putBytes(mockStorageRef, notNullbytes)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putBytes(notNullbytes);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putBytesNoData() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putBytes(mockStorageRef, nullBytes)
                        .test();

        RxTestUtil.testOnFailureListener.getValue().onFailure(RxTestUtil.NULL_FIREBASE_EXCEPTION);

        verify(mockStorageRef).putBytes(nullBytes);

        storageTestObserver.assertError(RxTestUtil.NULL_FIREBASE_EXCEPTION)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertNotComplete()
                .dispose();
    }

    @Test
    public void putBytesMetadata() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putBytes(mockStorageRef, notNullbytes, metadata)
                        .test();


        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);
        RxTestUtil.testOnCompleteListener.getValue().onComplete(mockVoidTask);

        verify(mockStorageRef).putBytes(notNullbytes, metadata);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putFile() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putFile(mockStorageRef, uri)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putFile(uri);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putFileMetadata() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putFile(mockStorageRef, uri, metadata)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putFile(uri, metadata);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putFileMetadataAndUri() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putFile(mockStorageRef, uri, metadata, uri)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putFile(uri, metadata, uri);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putStream() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putStream(mockStorageRef, stream)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putStream(stream);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void putStreamMetadata() {

        TestObserver<UploadTask.TaskSnapshot> storageTestObserver =
                RxFirebaseStorage.putStream(mockStorageRef, stream, metadata)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(uploadSnapshot);

        verify(mockStorageRef).putStream(stream, metadata);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(uploadSnapshot))
                .assertComplete()
                .dispose();
    }

    @Test
    public void updateMetadata() {

        TestObserver<StorageMetadata> storageTestObserver =
                RxFirebaseStorage.updateMetadata(mockStorageRef, metadata)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(metadata);

        verify(mockStorageRef).updateMetadata(metadata);

        storageTestObserver.assertNoErrors()
                .assertValueCount(1)
                //.assertValueSet(Collections.singletonList(metadata))
                .assertComplete()
                .dispose();
    }

    @Test
    public void delete() {

        TestObserver<Void> storageTestObserver =
                RxFirebaseStorage.delete(mockStorageRef)
                        .test();

        RxTestUtil.testOnSuccessListener.getValue().onSuccess(voidData);
        RxTestUtil.testOnCompleteListener.getValue().onComplete(mockVoidTask);

        verify(mockStorageRef).delete();

        storageTestObserver.assertNoErrors()
                .assertComplete()
                .dispose();
    }
}
