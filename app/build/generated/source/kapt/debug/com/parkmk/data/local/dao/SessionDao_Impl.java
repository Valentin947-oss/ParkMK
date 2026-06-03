package com.parkmk.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.parkmk.data.local.entity.SessionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SessionDao_Impl implements SessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SessionEntity> __insertionAdapterOfSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSessionEntity = new EntityInsertionAdapter<SessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sessions` (`id`,`cityId`,`cityName`,`spotId`,`spotName`,`zoneName`,`zoneLetter`,`vehicleId`,`plate`,`durationSeconds`,`pricePerHour`,`totalCost`,`smsNumber`,`smsBody`,`status`,`startTime`,`endTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SessionEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getCityId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCityId());
        }
        if (entity.getCityName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCityName());
        }
        if (entity.getSpotId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSpotId());
        }
        if (entity.getSpotName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSpotName());
        }
        if (entity.getZoneName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getZoneName());
        }
        if (entity.getZoneLetter() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getZoneLetter());
        }
        if (entity.getVehicleId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getVehicleId());
        }
        if (entity.getPlate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPlate());
        }
        statement.bindLong(10, entity.getDurationSeconds());
        statement.bindLong(11, entity.getPricePerHour());
        statement.bindDouble(12, entity.getTotalCost());
        if (entity.getSmsNumber() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getSmsNumber());
        }
        if (entity.getSmsBody() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getSmsBody());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getStatus());
        }
        statement.bindLong(16, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(17);
        } else {
          statement.bindLong(17, entity.getEndTime());
        }
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sessions SET status = ?, endTime = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sessions";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SessionEntity session, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSessionEntity.insert(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final String id, final String status, final Long endTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (endTime == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, endTime);
        }
        _argIndex = 3;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SessionEntity>> observeAll() {
    final String _sql = "SELECT * FROM sessions ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sessions"}, new Callable<List<SessionEntity>>() {
      @Override
      @NonNull
      public List<SessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfSpotId = CursorUtil.getColumnIndexOrThrow(_cursor, "spotId");
          final int _cursorIndexOfSpotName = CursorUtil.getColumnIndexOrThrow(_cursor, "spotName");
          final int _cursorIndexOfZoneName = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneName");
          final int _cursorIndexOfZoneLetter = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneLetter");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfPlate = CursorUtil.getColumnIndexOrThrow(_cursor, "plate");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfPricePerHour = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerHour");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfSmsNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "smsNumber");
          final int _cursorIndexOfSmsBody = CursorUtil.getColumnIndexOrThrow(_cursor, "smsBody");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final List<SessionEntity> _result = new ArrayList<SessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SessionEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCityId;
            if (_cursor.isNull(_cursorIndexOfCityId)) {
              _tmpCityId = null;
            } else {
              _tmpCityId = _cursor.getString(_cursorIndexOfCityId);
            }
            final String _tmpCityName;
            if (_cursor.isNull(_cursorIndexOfCityName)) {
              _tmpCityName = null;
            } else {
              _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            }
            final String _tmpSpotId;
            if (_cursor.isNull(_cursorIndexOfSpotId)) {
              _tmpSpotId = null;
            } else {
              _tmpSpotId = _cursor.getString(_cursorIndexOfSpotId);
            }
            final String _tmpSpotName;
            if (_cursor.isNull(_cursorIndexOfSpotName)) {
              _tmpSpotName = null;
            } else {
              _tmpSpotName = _cursor.getString(_cursorIndexOfSpotName);
            }
            final String _tmpZoneName;
            if (_cursor.isNull(_cursorIndexOfZoneName)) {
              _tmpZoneName = null;
            } else {
              _tmpZoneName = _cursor.getString(_cursorIndexOfZoneName);
            }
            final String _tmpZoneLetter;
            if (_cursor.isNull(_cursorIndexOfZoneLetter)) {
              _tmpZoneLetter = null;
            } else {
              _tmpZoneLetter = _cursor.getString(_cursorIndexOfZoneLetter);
            }
            final String _tmpVehicleId;
            if (_cursor.isNull(_cursorIndexOfVehicleId)) {
              _tmpVehicleId = null;
            } else {
              _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            }
            final String _tmpPlate;
            if (_cursor.isNull(_cursorIndexOfPlate)) {
              _tmpPlate = null;
            } else {
              _tmpPlate = _cursor.getString(_cursorIndexOfPlate);
            }
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final int _tmpPricePerHour;
            _tmpPricePerHour = _cursor.getInt(_cursorIndexOfPricePerHour);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpSmsNumber;
            if (_cursor.isNull(_cursorIndexOfSmsNumber)) {
              _tmpSmsNumber = null;
            } else {
              _tmpSmsNumber = _cursor.getString(_cursorIndexOfSmsNumber);
            }
            final String _tmpSmsBody;
            if (_cursor.isNull(_cursorIndexOfSmsBody)) {
              _tmpSmsBody = null;
            } else {
              _tmpSmsBody = _cursor.getString(_cursorIndexOfSmsBody);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _item = new SessionEntity(_tmpId,_tmpCityId,_tmpCityName,_tmpSpotId,_tmpSpotName,_tmpZoneName,_tmpZoneLetter,_tmpVehicleId,_tmpPlate,_tmpDurationSeconds,_tmpPricePerHour,_tmpTotalCost,_tmpSmsNumber,_tmpSmsBody,_tmpStatus,_tmpStartTime,_tmpEndTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRecent(final Continuation<? super List<SessionEntity>> $completion) {
    final String _sql = "SELECT * FROM sessions ORDER BY startTime DESC LIMIT 50";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SessionEntity>>() {
      @Override
      @NonNull
      public List<SessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfSpotId = CursorUtil.getColumnIndexOrThrow(_cursor, "spotId");
          final int _cursorIndexOfSpotName = CursorUtil.getColumnIndexOrThrow(_cursor, "spotName");
          final int _cursorIndexOfZoneName = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneName");
          final int _cursorIndexOfZoneLetter = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneLetter");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfPlate = CursorUtil.getColumnIndexOrThrow(_cursor, "plate");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfPricePerHour = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerHour");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfSmsNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "smsNumber");
          final int _cursorIndexOfSmsBody = CursorUtil.getColumnIndexOrThrow(_cursor, "smsBody");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final List<SessionEntity> _result = new ArrayList<SessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SessionEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCityId;
            if (_cursor.isNull(_cursorIndexOfCityId)) {
              _tmpCityId = null;
            } else {
              _tmpCityId = _cursor.getString(_cursorIndexOfCityId);
            }
            final String _tmpCityName;
            if (_cursor.isNull(_cursorIndexOfCityName)) {
              _tmpCityName = null;
            } else {
              _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            }
            final String _tmpSpotId;
            if (_cursor.isNull(_cursorIndexOfSpotId)) {
              _tmpSpotId = null;
            } else {
              _tmpSpotId = _cursor.getString(_cursorIndexOfSpotId);
            }
            final String _tmpSpotName;
            if (_cursor.isNull(_cursorIndexOfSpotName)) {
              _tmpSpotName = null;
            } else {
              _tmpSpotName = _cursor.getString(_cursorIndexOfSpotName);
            }
            final String _tmpZoneName;
            if (_cursor.isNull(_cursorIndexOfZoneName)) {
              _tmpZoneName = null;
            } else {
              _tmpZoneName = _cursor.getString(_cursorIndexOfZoneName);
            }
            final String _tmpZoneLetter;
            if (_cursor.isNull(_cursorIndexOfZoneLetter)) {
              _tmpZoneLetter = null;
            } else {
              _tmpZoneLetter = _cursor.getString(_cursorIndexOfZoneLetter);
            }
            final String _tmpVehicleId;
            if (_cursor.isNull(_cursorIndexOfVehicleId)) {
              _tmpVehicleId = null;
            } else {
              _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            }
            final String _tmpPlate;
            if (_cursor.isNull(_cursorIndexOfPlate)) {
              _tmpPlate = null;
            } else {
              _tmpPlate = _cursor.getString(_cursorIndexOfPlate);
            }
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final int _tmpPricePerHour;
            _tmpPricePerHour = _cursor.getInt(_cursorIndexOfPricePerHour);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpSmsNumber;
            if (_cursor.isNull(_cursorIndexOfSmsNumber)) {
              _tmpSmsNumber = null;
            } else {
              _tmpSmsNumber = _cursor.getString(_cursorIndexOfSmsNumber);
            }
            final String _tmpSmsBody;
            if (_cursor.isNull(_cursorIndexOfSmsBody)) {
              _tmpSmsBody = null;
            } else {
              _tmpSmsBody = _cursor.getString(_cursorIndexOfSmsBody);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _item = new SessionEntity(_tmpId,_tmpCityId,_tmpCityName,_tmpSpotId,_tmpSpotName,_tmpZoneName,_tmpZoneLetter,_tmpVehicleId,_tmpPlate,_tmpDurationSeconds,_tmpPricePerHour,_tmpTotalCost,_tmpSmsNumber,_tmpSmsBody,_tmpStatus,_tmpStartTime,_tmpEndTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SessionEntity> observeActive() {
    final String _sql = "SELECT * FROM sessions WHERE status = 'ACTIVE' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sessions"}, new Callable<SessionEntity>() {
      @Override
      @Nullable
      public SessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfSpotId = CursorUtil.getColumnIndexOrThrow(_cursor, "spotId");
          final int _cursorIndexOfSpotName = CursorUtil.getColumnIndexOrThrow(_cursor, "spotName");
          final int _cursorIndexOfZoneName = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneName");
          final int _cursorIndexOfZoneLetter = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneLetter");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfPlate = CursorUtil.getColumnIndexOrThrow(_cursor, "plate");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfPricePerHour = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerHour");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfSmsNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "smsNumber");
          final int _cursorIndexOfSmsBody = CursorUtil.getColumnIndexOrThrow(_cursor, "smsBody");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final SessionEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCityId;
            if (_cursor.isNull(_cursorIndexOfCityId)) {
              _tmpCityId = null;
            } else {
              _tmpCityId = _cursor.getString(_cursorIndexOfCityId);
            }
            final String _tmpCityName;
            if (_cursor.isNull(_cursorIndexOfCityName)) {
              _tmpCityName = null;
            } else {
              _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            }
            final String _tmpSpotId;
            if (_cursor.isNull(_cursorIndexOfSpotId)) {
              _tmpSpotId = null;
            } else {
              _tmpSpotId = _cursor.getString(_cursorIndexOfSpotId);
            }
            final String _tmpSpotName;
            if (_cursor.isNull(_cursorIndexOfSpotName)) {
              _tmpSpotName = null;
            } else {
              _tmpSpotName = _cursor.getString(_cursorIndexOfSpotName);
            }
            final String _tmpZoneName;
            if (_cursor.isNull(_cursorIndexOfZoneName)) {
              _tmpZoneName = null;
            } else {
              _tmpZoneName = _cursor.getString(_cursorIndexOfZoneName);
            }
            final String _tmpZoneLetter;
            if (_cursor.isNull(_cursorIndexOfZoneLetter)) {
              _tmpZoneLetter = null;
            } else {
              _tmpZoneLetter = _cursor.getString(_cursorIndexOfZoneLetter);
            }
            final String _tmpVehicleId;
            if (_cursor.isNull(_cursorIndexOfVehicleId)) {
              _tmpVehicleId = null;
            } else {
              _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            }
            final String _tmpPlate;
            if (_cursor.isNull(_cursorIndexOfPlate)) {
              _tmpPlate = null;
            } else {
              _tmpPlate = _cursor.getString(_cursorIndexOfPlate);
            }
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final int _tmpPricePerHour;
            _tmpPricePerHour = _cursor.getInt(_cursorIndexOfPricePerHour);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpSmsNumber;
            if (_cursor.isNull(_cursorIndexOfSmsNumber)) {
              _tmpSmsNumber = null;
            } else {
              _tmpSmsNumber = _cursor.getString(_cursorIndexOfSmsNumber);
            }
            final String _tmpSmsBody;
            if (_cursor.isNull(_cursorIndexOfSmsBody)) {
              _tmpSmsBody = null;
            } else {
              _tmpSmsBody = _cursor.getString(_cursorIndexOfSmsBody);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _result = new SessionEntity(_tmpId,_tmpCityId,_tmpCityName,_tmpSpotId,_tmpSpotName,_tmpZoneName,_tmpZoneLetter,_tmpVehicleId,_tmpPlate,_tmpDurationSeconds,_tmpPricePerHour,_tmpTotalCost,_tmpSmsNumber,_tmpSmsBody,_tmpStatus,_tmpStartTime,_tmpEndTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
