package com.parkmk.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.parkmk.data.local.entity.SpotEntity;
import java.lang.Class;
import java.lang.Exception;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SpotDao_Impl implements SpotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SpotEntity> __insertionAdapterOfSpotEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SpotDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSpotEntity = new EntityInsertionAdapter<SpotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `spots` (`id`,`name`,`address`,`zoneId`,`zoneName`,`zoneLetter`,`pricePerHour`,`totalSpots`,`openFrom`,`openUntil`,`latitude`,`longitude`,`isActive`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SpotEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAddress());
        }
        if (entity.getZoneId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getZoneId());
        }
        if (entity.getZoneName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getZoneName());
        }
        if (entity.getZoneLetter() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getZoneLetter());
        }
        statement.bindLong(7, entity.getPricePerHour());
        statement.bindLong(8, entity.getTotalSpots());
        if (entity.getOpenFrom() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getOpenFrom());
        }
        if (entity.getOpenUntil() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getOpenUntil());
        }
        statement.bindDouble(11, entity.getLatitude());
        statement.bindDouble(12, entity.getLongitude());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp);
        statement.bindLong(14, entity.getCachedAt());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM spots";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<SpotEntity> spots,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSpotEntity.insert(spots);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Object getAll(final Continuation<? super List<SpotEntity>> $completion) {
    final String _sql = "SELECT * FROM spots WHERE isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SpotEntity>>() {
      @Override
      @NonNull
      public List<SpotEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfZoneId = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneId");
          final int _cursorIndexOfZoneName = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneName");
          final int _cursorIndexOfZoneLetter = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneLetter");
          final int _cursorIndexOfPricePerHour = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerHour");
          final int _cursorIndexOfTotalSpots = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpots");
          final int _cursorIndexOfOpenFrom = CursorUtil.getColumnIndexOrThrow(_cursor, "openFrom");
          final int _cursorIndexOfOpenUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "openUntil");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final List<SpotEntity> _result = new ArrayList<SpotEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SpotEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpZoneId;
            if (_cursor.isNull(_cursorIndexOfZoneId)) {
              _tmpZoneId = null;
            } else {
              _tmpZoneId = _cursor.getString(_cursorIndexOfZoneId);
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
            final int _tmpPricePerHour;
            _tmpPricePerHour = _cursor.getInt(_cursorIndexOfPricePerHour);
            final int _tmpTotalSpots;
            _tmpTotalSpots = _cursor.getInt(_cursorIndexOfTotalSpots);
            final String _tmpOpenFrom;
            if (_cursor.isNull(_cursorIndexOfOpenFrom)) {
              _tmpOpenFrom = null;
            } else {
              _tmpOpenFrom = _cursor.getString(_cursorIndexOfOpenFrom);
            }
            final String _tmpOpenUntil;
            if (_cursor.isNull(_cursorIndexOfOpenUntil)) {
              _tmpOpenUntil = null;
            } else {
              _tmpOpenUntil = _cursor.getString(_cursorIndexOfOpenUntil);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _item = new SpotEntity(_tmpId,_tmpName,_tmpAddress,_tmpZoneId,_tmpZoneName,_tmpZoneLetter,_tmpPricePerHour,_tmpTotalSpots,_tmpOpenFrom,_tmpOpenUntil,_tmpLatitude,_tmpLongitude,_tmpIsActive,_tmpCachedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
